using APICORE.Entities;
using System.ComponentModel.DataAnnotations;

namespace APICORE.Contracts
{
    public class TransactionRequest
    {
        [Required]
        public string Client_id { get; set; }

        [Required]
        public string Client_name { get; set; }

        [Required]
        public int Total_to_pay { get; set; }

        [Required]
        public Credit_Card Credit_card { get; set; }
    }
}
