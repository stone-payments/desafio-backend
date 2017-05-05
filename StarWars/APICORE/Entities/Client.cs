using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace APICORE.Entities
{
    [Table("Client")]
    public class Client
    {
        [Key]
        public int Id { get; set; }
        public string ClientId { get; set; }
        public string ClientName { get; set; }
    }
}
