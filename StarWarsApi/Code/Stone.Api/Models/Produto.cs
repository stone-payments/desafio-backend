using LiteDB;
using Newtonsoft.Json;
using System;

namespace Stone.Api.Models
{
    public class Produto
    {
        [JsonIgnore]
        public Guid Id { get; set; }

        [JsonProperty(PropertyName ="title")]
        public string Title { get; set; }

        [JsonProperty(PropertyName = "price")]
        public int Price { get; set; }

        [JsonProperty(PropertyName = "zipcode")]
        public string ZipCode { get; set; }

        [JsonProperty(PropertyName = "seller")]
        public string Seller { get; set; }

        [JsonProperty(PropertyName = "thumbnailHd")]
        public string ThumbnailHd { get; set; }
        [JsonProperty(PropertyName = "date")]
        public string Date { get; set; }
    }
}