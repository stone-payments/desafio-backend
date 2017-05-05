using APICORE.Entities;
using System.Data.Entity;
using System.Data.Entity.ModelConfiguration.Conventions;

namespace APICORE.Data
{
    public class StarStoreContext : DbContext
    {
        public DbSet<Product> Products { get; set; }
        public DbSet<Transaction> Transactions { get; set; }
        public DbSet<Credit_Card> Credit_Cards { get; set; }
        public DbSet<Client> Clients { get; set; }

        public StarStoreContext() : base("StarStoreDb")
        {
            Database.SetInitializer<StarStoreContext>(null);
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Conventions.Remove<PluralizingTableNameConvention>();
        }
    }
}
