using Stone.Api.Attributes;
using Stone.Api.Models;
using Stone.Api.Services;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Web.Http;
using WebApi.OutputCache.V2;

namespace Stone.Api.Controllers
{
    [RoutePrefix("Starstore")]
    [Authorization(Users = "STONE")]
    public class HistoricoCompraController : ApiController
    {
        private readonly IHistoricoCompraService _service;
        public HistoricoCompraController(IHistoricoCompraService service)
        {
            _service = service;
        }

        /// <summary>
        /// Esse método deve retornar todos as compras realizadas na API.
        /// </summary>
        /// <returns></returns>
        [Route("history"), HttpPost]
        public async Task<IEnumerable<HistoricoCompra>> ComprasRealizadas()
        {
            try
            {
                return await _service.ListarAsync();
            }
            catch (Exception e)
            {
                throw new Exception("Erro ao tentar buscar a lista de compras");
            }
        }

        /// <summary>
        /// Esse método deve retornar todos as compras realizadas na API por um cliente específico. 
        /// </summary>
        /// <param name="clienteId"></param>
        /// <returns></returns>
        /// Cache com 120 segundos
        [Route("history/{clientId}"), HttpPost]
        [CacheOutput(ServerTimeSpan = 120)]
        public async Task<IEnumerable<HistoricoCompra>> ComprasRealizadasPorCliente(string clienteId)
        {
            try
            {
                return await _service.ListarAsync(x => x.ClientId == clienteId);
            }
            catch (Exception e)
            {
                throw new Exception("Erro ao tentar buscar a lista de compras");
            }
        }
    }
}
