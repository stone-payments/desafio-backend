using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace APICORE.Entities
{
    [Table("CreditCard_Transaction")]
    public class Credit_Card
    {
        [Key]
        public int Id { get; set; }
        public int TransactionId { get; set; }
        public string Card_number { get; set; }
        public int Value { get; set; }
        public int Cvv { get; set; }
        public string Card_holder_name { get; set; }
        public string Exp_date { get; set; }
        public virtual Transaction Transaction { get; set; }
    }
}
