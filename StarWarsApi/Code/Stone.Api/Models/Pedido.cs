using LiteDB;
using Newtonsoft.Json;
using System;

namespace Stone.Api.Models
{
    public class Compra
    {
        [JsonIgnore]
        public Guid Id { get; set; }
        public string Client_id { get; set; }
        public string Client_name { get; set; }
        public int Total_to_pay { get; set; }
        public CartaoDeCredito Credit_card { get; set; }
        [JsonIgnore]
        public Produto Produto { get; set; }
        [JsonIgnore]
        public string Criacao { get { return DateTime.Now.ToString("dd/MM/yyyy"); } }
    }
}