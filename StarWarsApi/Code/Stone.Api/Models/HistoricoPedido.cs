using Newtonsoft.Json;

namespace Stone.Api.Models
{
    public class HistoricoCompra
    {
        [JsonProperty(PropertyName = "client_id")]
        public string ClientId { get; set; }

        [JsonProperty(PropertyName = "purchase_id")]
        public string Purchase_id {
            //get {
            //    if (Produto == null)
            //        return "569c30dc-6bdb-407a-b18b-3794f9b206a8";
            //    return Produto.Id.ToString(); }
            get; set;
        }

        [JsonProperty(PropertyName = "value")]
        public int Value { get; set; }

        [JsonProperty(PropertyName = "date")]
        public string Date { get; set; }

        [JsonProperty(PropertyName = "card_number")]
        public string Card_number { get; set; }
    }
}