using Stone.Api.Attributes;
using Stone.Api.Models;
using Stone.Api.Services;
using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using WebApi.OutputCache.V2;

namespace Stone.Api.Controllers
{
    [RoutePrefix("Starstore")]
    [Authorization(Users = "STONE")]
    public class ProdutoController : ApiController
    {
        private IProdutoService _service { get; set; }
        public ProdutoController(IProdutoService service)
        {
            this._service = service;
        }

        /// <summary>
        /// Esse método deve receber um produto novo e inseri-lo em um banco de dados. - Não implementado
        /// </summary>
        /// <param name="pedido"></param>
        /// <returns></returns>
        [Route("Product"), HttpPost]
        [CacheOutput(ServerTimeSpan = 120)]
        public async Task<HttpResponseMessage> IncluirProduto([FromBody]Produto pedido)
        {
            try
            {
                await _service.IncluirAsync(pedido);
                return Request.CreateResponse(HttpStatusCode.OK, "Produto incluído com sucesso");
            }
            catch (System.Exception e)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, e);
            }
        }

        /// <summary>
        /// Esse método retornará todos os pedidos da API.
        /// </summary>
        /// <returns></returns>
        [Route("Products"), HttpGet]
        [CacheOutput(ServerTimeSpan = 120)]
        public async Task<IEnumerable<Produto>> ListarProdutos()
        {
            try
            {
                return await _service.ListarAsync();
            }
            catch (Exception e)
            {
                throw new Exception("Erro ao tentar buscar a lista de produtos");
            }
        }
    }
}
