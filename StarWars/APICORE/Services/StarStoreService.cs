using APICORE.Contracts;
using APICORE.Data;
using APICORE.Entities;
using APICORE.Services.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

namespace APICORE.Services
{
    public class StarStoreService : IStarStoreService
    {
        private readonly StarStoreContext _db = new StarStoreContext();
        public async Task<HttpStatusCode> SaveProduct(Contracts.Product productRequest)
        {
            try
            {
                var product = new Entities.Product
                {
                    Title = productRequest.Title,
                    Price = productRequest.Price,
                    Seller = productRequest.Seller,
                    Zipcode = productRequest.Zipcode,
                    Date = productRequest.Date,
                    ThumbnailHd = productRequest.ThumbnailHd
                };

                _db.Products.Add(product);
                await _db.SaveChangesAsync();

                return HttpStatusCode.OK;
            }
            catch (Exception ex)
            {
                return HttpStatusCode.InternalServerError;
            }
        }

        public IEnumerable<Contracts.Product> GetProducts()
        {
            var products = _db.Products;
            if (products == null)
                return new List<Contracts.Product>();

            var result = products.Select(p => new Contracts.Product
            {
                Title = p.Title,
                Date = p.Date,
                Price = p.Price,
                Seller = p.Seller,
                Zipcode = p.Zipcode,
                ThumbnailHd = p.ThumbnailHd
            }).ToList();
                   
           return result;
        }

        public async Task<HttpStatusCode> SaveTransaction(TransactionRequest transactionRequest)
        {
            try
            {
                var transaction = new Transaction
                {
                    Client_id = transactionRequest.Client_id,
                    Client_name = transactionRequest.Client_name,
                    Total_to_pay = transactionRequest.Total_to_pay,
                    Purchase_Id = Guid.NewGuid(),
                    TransactionDate = DateTime.Now.ToString(),
                    Credit_card = new List<Credit_Card> { transactionRequest.Credit_card }
                };
                _db.Transactions.Add(transaction);
                await _db.SaveChangesAsync();

                return HttpStatusCode.OK;
            }
            catch (Exception ex)
            {
                return HttpStatusCode.InternalServerError;
            }
        }
        public IEnumerable<TransactionResponse> GetHistory()
        {
            var transactions = _db.Transactions;

            if (transactions == null)
                return new List<TransactionResponse>();

            var response = transactions.Select(t => new TransactionResponse
            {
                Client_id = t.Client_id,
                Card_number = t.Credit_card.FirstOrDefault(x=>x.TransactionId == t.Id).Card_number,
                Value = t.Total_to_pay,
                Purchase_id = t.Purchase_Id.ToString(),
                Date = t.TransactionDate
            }).ToList();

            return response;
        }

        public IEnumerable<TransactionResponse> HistoryByClientId(string id)
        {

            var transactions = _db.Transactions;

            if (transactions == null)
                return new List<TransactionResponse>();

            var response = transactions.Where(t=>t.Client_id == id).Select(t => new TransactionResponse
            {
                Client_id = t.Client_id,
                Card_number = t.Credit_card.FirstOrDefault(x => x.TransactionId == t.Id).Card_number,
                Value = t.Total_to_pay,
                Purchase_id = t.Purchase_Id.ToString(),
                Date = t.TransactionDate
            }).ToList();

            return response;
            
        }
    }
}
