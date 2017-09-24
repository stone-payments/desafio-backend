using SimpleInjector;
using SimpleInjector.Integration.WebApi;
using SimpleInjector.Lifestyles;
using Stone.Api.Repositories;
using Stone.Api.Services;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Routing;

namespace Stone.Api
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            // Criar o Container
            var container = new Container();
            container.Options.DefaultScopedLifestyle = new AsyncScopedLifestyle();
            // Registrar seus tipos para instanciar usando o Lifestyle Scoped
            container.Register<IProdutoService, ProdutoService>(Lifestyle.Scoped);
            container.Register<IProdutoRepository, ProdutoRepository>(Lifestyle.Scoped);
            container.Register<ICompraService, CompraService>(Lifestyle.Scoped);
            container.Register<ICompraRepository, CompraRepository>(Lifestyle.Scoped);
            container.Register<IHistoricoCompraService, HistoricoCompraService>(Lifestyle.Scoped);
            container.Register<IHistoricoCompraRepository, HistoricoCompraRepository>(Lifestyle.Scoped);
            // Isso é um extension method de integridade do pacote
            container.RegisterWebApiControllers(GlobalConfiguration.Configuration);
            container.Verify();

            GlobalConfiguration.Configuration.DependencyResolver =
                new SimpleInjectorWebApiDependencyResolver(container);
            
            GlobalConfiguration.Configure(WebApiConfig.Register);
        }
        protected void Application_Error(Object sender, EventArgs e)
        {
            try
            {
                var ex = Server.GetLastError();
                string uri = null;
                if (Context != null && Context.Request != null)
                {
                    uri = Context.Request.Url.AbsoluteUri;
                }
                var erroGuid = Guid.NewGuid().ToString("N");

                var httpEx = ex as HttpException;
                Session["ErroKey"] = erroGuid;
                Session["httpEx"] = httpEx;

                //Usuario não autenticado
                if ((httpEx != null && httpEx.GetHttpCode() == 401)
                    || (uri != null && Context.Response.StatusCode == 401))
                {
                    Debug.WriteLine("Usuário não autenticado", erroGuid, ex);
                    Server.ClearError();
                    Server.TransferRequest("~/Home");
                }
                else if ((httpEx != null && httpEx.GetHttpCode() == 403)
                         || (uri != null && Context.Response.StatusCode == 403))
                {
                    Debug.WriteLine("Acesso negado", erroGuid, ex);
                    Server.ClearError();
                    Server.TransferRequest("~/Home");
                }
                else if ((httpEx != null && httpEx.GetHttpCode() == 404)
                         || (uri != null && Context.Response.StatusCode == 404))
                {
                    Debug.WriteLine("Página não encontrada", erroGuid, ex);
                    Server.ClearError();
                    Server.TransferRequest("~/Home");
                }
                else if ((httpEx != null && httpEx.GetHttpCode() == 500)
                         || (uri != null && Context.Response.StatusCode == 500))
                {
                    Debug.WriteLine("Erro inesperado", erroGuid, ex);
                    Server.ClearError();
                    Server.TransferRequest("~/Home");
                }
                else
                {
                    Debug.WriteLine("Erro inesperado", erroGuid, ex);
                    Server.ClearError();
                    Server.TransferRequest("~/Home");
                }
            }
            catch (Exception ex)
            {
                Debug.WriteLine("Erro inesperado");
                Server.ClearError();
                //Server.TransferRequest("~/Home");
                Debug.WriteLine(ex);
            }
        }

    }
}
