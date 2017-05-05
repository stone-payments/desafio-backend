using System.Collections.Generic;
using APICORE.Contracts;
using System.Threading.Tasks;
using System.Net;

namespace APICORE.Services.Interfaces
{
    public interface IStarStoreService
    {
        IEnumerable<TransactionResponse> GetHistory();
        IEnumerable<Product> GetProducts();
        IEnumerable<TransactionResponse> HistoryByClientId(string id);
        Task<HttpStatusCode> SaveProduct(Product productRequest);
        Task<HttpStatusCode> SaveTransaction(TransactionRequest transactionRequest);
    }
}