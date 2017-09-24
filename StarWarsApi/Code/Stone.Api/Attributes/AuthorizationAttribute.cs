using System;
using System.Linq;
using System.Security.Authentication;
using System.Security.Claims;
using System.Security.Principal;
using System.Text;
using System.Web;
using System.Web.Http.Controllers;
using System.Web.Http.Filters;

namespace Stone.Api.Attributes
{
    [AttributeUsage(AttributeTargets.Class | AttributeTargets.Method, Inherited = true, AllowMultiple = true)]
    public class AuthorizationAttribute : AuthorizationFilterAttribute
    {
        public string Users { get; set; }

        public override void OnAuthorization(HttpActionContext actionContext)
        {
            var identity = ParseAuthorizationHeader(actionContext);
            var user = HttpContext.Current.User as ClaimsPrincipal;
            //string users = HttpContext.Current.User.Identity.Name;
            string users = identity.Claims.FirstOrDefault(x => x.Type == "Empresa").Value;
            if (Users.Split(',').All(x => x != users))
                throw new AuthenticationException("Erro na autenticação de usuário.");
            base.OnAuthorization(actionContext);
        }

        protected virtual BasicAuthenticationIdentity ParseAuthorizationHeader(HttpActionContext actionContext)
        {
            string authHeader = null;
            var auth = actionContext.Request.Headers.Authorization;
            if (auth != null && auth.Scheme == "Basic")
                authHeader = auth.Parameter;

            if (string.IsNullOrEmpty(authHeader))
                throw new AuthenticationException("Erro na autenticação de usuário.");

            authHeader = Encoding.Default.GetString(Convert.FromBase64String(authHeader));

            var tokens = authHeader.Split(':');
            if (tokens.Length < 2)
                throw new AuthenticationException("Erro na autenticação de usuário.");

            if(tokens[0] != "admin" && tokens[1] != "admin")
                throw new AuthenticationException("Erro na autenticação de usuário.");

            return new BasicAuthenticationIdentity(tokens[0], tokens[1]);
        }
    }

    public class BasicAuthenticationIdentity : GenericIdentity
    {
        public BasicAuthenticationIdentity(string name, string password)
            : base(name, "Basic")
        {
            Password = password;
            AddClaim(new Claim("Empresa", "STONE"));
        }

        /// <summary>
        /// Basic Auth Password for custom authentication
        /// </summary>
        public string Password { get; set; }

    }
}