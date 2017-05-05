using System.ComponentModel.DataAnnotations;

namespace APICORE.Contracts
{
    public class Product
    {
        [Required]
        public string Title { get; set; }

        [Required]
        public int Price { get; set; }

        [Required]
        public string Zipcode { get; set; }

        [Required]
        public string Seller { get; set; }

        [Required]
        public string ThumbnailHd { get; set; }

        [Required]
        public string Date { get; set; }
    }
}
