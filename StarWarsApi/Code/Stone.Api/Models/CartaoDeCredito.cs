using LiteDB;
using Newtonsoft.Json;
using System;

namespace Stone.Api.Models
{
    public class CartaoDeCredito
    {
        [JsonIgnore]
        public Guid Id { get; set; }
        public string Card_number { get; set; }
        public string Card_holder_name { get; set; }
        public int Value { get; set; }
        public int Cvv { get; set; }
        public string Exp_date { get; set; }
    }
}