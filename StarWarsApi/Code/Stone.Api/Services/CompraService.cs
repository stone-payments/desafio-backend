using Stone.Api.Models;
using Stone.Api.Repositories;
using System.Threading.Tasks;

namespace Stone.Api.Services
{
    public interface ICompraService
    {
        Compra RealizarCompra(Compra pedido);
        Task<Compra> RealizarCompraAsync(Compra pedido);
    }

    public class CompraService : ICompraService
    {
        private readonly ICompraRepository _repository;
        public CompraService(ICompraRepository repository)
        {
            _repository = repository;
        }
        public Compra RealizarCompra(Compra pedido)
        {
            return _repository.InsertPedido(pedido);
        }

        public async Task<Compra> RealizarCompraAsync(Compra pedido)
        {
            return await Task.FromResult(RealizarCompra(pedido));
        }
    }
}