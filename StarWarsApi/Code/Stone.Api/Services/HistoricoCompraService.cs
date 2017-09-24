using Stone.Api.Models;
using Stone.Api.Repositories;
using System;
using System.Collections.Generic;
using System.Linq.Expressions;
using System.Threading.Tasks;

namespace Stone.Api.Services
{
    public interface IHistoricoCompraService
    {
        List<HistoricoCompra> Listar();
        List<HistoricoCompra> Listar(Expression<Func<HistoricoCompra, bool>> filtro);
        Task<IEnumerable<HistoricoCompra>> ListarAsync();
        Task<IEnumerable<HistoricoCompra>> ListarAsync(Expression<Func<HistoricoCompra, bool>> filtro);
    }
    public class HistoricoCompraService : IHistoricoCompraService
    {
        private IHistoricoCompraRepository _repositorio { get; set; }

        public HistoricoCompraService()
        {
            _repositorio = new HistoricoCompraRepository();
        }

        public List<HistoricoCompra> Listar()
        {
            return _repositorio.GetCompras();
        }

        public List<HistoricoCompra> Listar(Expression<Func<HistoricoCompra, bool>> filtro)
        {
            return _repositorio.GetCompras(filtro);
        }

        public async Task<IEnumerable<HistoricoCompra>> ListarAsync()
        {
            return await Task.FromResult(Listar());
        }

        public async Task<IEnumerable<HistoricoCompra>> ListarAsync(Expression<Func<HistoricoCompra, bool>> filtro)
        {
            return await Task.FromResult(Listar(filtro));
        }
    }
}