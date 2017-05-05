using System.Threading.Tasks;
using APICORE.Entities;

namespace APICORE.Services.Interfaces
{
    public interface IClientService
    {
        Task CreateClient(Client client);
        bool VerifyClient(string id);
    }
}