using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace APICORE.Entities
{
    [Table("Product")]
    public class Product
    {
        [Key]
        public int Id { get; set; }
        public string Title { get; set; }
        public int Price { get; set; }
        public string Zipcode { get; set; }
        public string Seller { get; set; }
        public string ThumbnailHd { get; set; }

        [Column("ProductDate")]
        public string Date { get; set; }
    }
}
