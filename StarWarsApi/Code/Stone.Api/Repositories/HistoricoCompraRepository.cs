using LiteDB;
using Stone.Api.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;

namespace Stone.Api.Repositories
{
    public interface IHistoricoCompraRepository
    {
        List<HistoricoCompra> GetCompras();
        List<HistoricoCompra> GetCompras(Expression<Func<HistoricoCompra, bool>> filtro);
    }

    public class HistoricoCompraRepository : IHistoricoCompraRepository
    {
        public string FullPath
        {
            get
            {
                return System.Web.Hosting.HostingEnvironment.MapPath(@"~/App_Data/Application.db");
            }
        }
        public List<HistoricoCompra> GetCompras()
        {
            using (var db = new LiteDatabase(FullPath))
            {
                var produtos = db.GetCollection<Compra>("Compra");

                return produtos.FindAll()
                    .Select(x => new HistoricoCompra
                    {
                        ClientId = x.Client_id,
                        Card_number = string.Format("**** **** **** {0}", x.Credit_card.Card_number.Substring(12)),
                        Date = x.Criacao,
                        Value = x.Total_to_pay,
                        Purchase_id = x.Produto == null? "569c30dc-6bdb-407a-b18b-3794f9b206a8" : x.Produto.Id.ToString()
                    })
                    .ToList();
            }
        }

        public List<HistoricoCompra> GetCompras(Expression<Func<HistoricoCompra, bool>> filtro)
        {
            using (var db = new LiteDatabase(FullPath))
            {
                var produtos = db.GetCollection<Compra>("Compra");

                return produtos.Find(x=> x.Client_id == "1")
                    .Select(x => new HistoricoCompra
                    {
                        ClientId = x.Client_id,
                        Card_number = x.Credit_card.Card_number,
                        Date = x.Criacao,
                        Value = x.Produto.Price,
                        Purchase_id = x.Id.ToString()
                    })
                    .ToList();
            }
        }
    }
}