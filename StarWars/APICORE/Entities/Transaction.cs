using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace APICORE.Entities
{
    [Table("Transaction")]
    public class Transaction
    {
        [Key]
        public int Id { get; set; }
        public string Client_id { get; set; }
        public string Client_name { get; set; }
        public int Total_to_pay { get; set; }
        public Guid Purchase_Id { get; set; }
        public string TransactionDate { get; set; }
        public virtual List<Credit_Card> Credit_card { get; set; }
    }
}
