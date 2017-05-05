using APICORE.Data;
using APICORE.Entities;
using APICORE.Services.Interfaces;
using System.Linq;
using System.Threading.Tasks;

namespace APICORE.Services
{
    public class ClientService : IClientService
    {
        private readonly StarStoreContext _db = new StarStoreContext();
        public async Task CreateClient(Client client)
        {
            _db.Clients.Add(client);
            await _db.SaveChangesAsync();
        }

        public bool VerifyClient(string id)
        {
            return _db.Clients.FirstOrDefault(c => c.ClientId == id) == null ? false : true;
        }
    }
}
