using Stone.Api.Attributes;
using Stone.Api.Models;
using Stone.Api.Services;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;

namespace Stone.Api.Controllers
{
    [RoutePrefix("Starstore")]
    [Authorization(Users = "STONE")]
    public class CompraController : ApiController
    {
        private readonly ICompraService _service;
        public CompraController(ICompraService service)
        {
            _service = service;
        }
        
        /// <summary>
        /// Esse método irá receber os dados da compra, junto com os dados do usuário.
        /// </summary>
        /// <param name="pedido"></param>
        /// <returns></returns>
        [Route("Buy"), HttpPost]
        public async Task<HttpResponseMessage> RealizarCompra([FromBody]Compra pedido)
        {
            try
            {
                await _service.RealizarCompraAsync(pedido);
                return Request.CreateResponse(HttpStatusCode.OK, "Compra concluída com sucesso");
            }
            catch (System.Exception e)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, e);
            }
        }
    }
}
