using LiteDB;
using Stone.Api.Models;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;

namespace Stone.Api.Repositories
{
    public interface IProdutoRepository
    {
        Produto InsertProduto(Produto pedido);
        List<Produto> GetProduto();
    }

    public class ProdutoRepository : IProdutoRepository
    {
        public string FullPath
        {
            get
            {
                return System.Web.Hosting.HostingEnvironment.MapPath(@"~/App_Data/Application.db");
            }
        }
        public List<Produto> GetProduto()
        {
            using (var db = new LiteDatabase(FullPath))
            {
                var produtos = db.GetCollection<Produto>("Produto");
                return produtos.FindAll().ToList();
            }
        }

        public Produto InsertProduto(Produto pedido)
        {
            using (var db = new LiteDatabase(FullPath))
            {
                var produtos = db.GetCollection<Produto>("Produto");
                produtos.Insert(pedido);
                produtos.EnsureIndex(x => x.Id);
            }
            return pedido;
        }
    }
}