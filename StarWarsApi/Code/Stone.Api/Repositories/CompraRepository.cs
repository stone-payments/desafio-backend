using LiteDB;
using Stone.Api.Models;

namespace Stone.Api.Repositories
{
    public interface ICompraRepository
    {
        Compra InsertPedido(Compra compra);
    }

    public class CompraRepository : ICompraRepository
    {
        public string FullPath
        {
            get
            {
                return System.Web.Hosting.HostingEnvironment.MapPath(@"~/App_Data/Application.db");
            }
        }

        public Compra InsertPedido(Compra compra)
        {
            using (var db = new LiteDatabase(FullPath))
            {
                var produtos = db.GetCollection<Compra>("Compra");
                produtos.Insert(compra);
                return compra;
            }
        }
    }

}