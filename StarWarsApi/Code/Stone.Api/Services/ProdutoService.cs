using Stone.Api.Models;
using Stone.Api.Repositories;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Stone.Api.Services
{
    public interface IProdutoService
    {
        IList<Produto> Listar();
        Task<IList<Produto>> ListarAsync();
        Produto Incluir(Produto pedido);
        Task<Produto> IncluirAsync(Produto pedido);
    }

    public class ProdutoService : IProdutoService
    {
        private IProdutoRepository _repositorio { get; set; }
        public ProdutoService(IProdutoRepository repositorio)
        {
            _repositorio = repositorio;
        }
        public IList<Produto> Listar()
        {
            return _repositorio.GetProduto();
        }

        public async Task<IList<Produto>> ListarAsync()
        {
            return await Task.FromResult(Listar());
        }

        public Produto Incluir(Produto pedido)
        {
            return _repositorio.InsertProduto(pedido);
        }

        public async Task<Produto> IncluirAsync(Produto pedido)
        {
            return await Task.FromResult(Incluir(pedido));
        }
    }
}