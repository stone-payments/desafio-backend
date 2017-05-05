using API.Filters;
using APICORE.Contracts;
using APICORE.Filters;
using APICORE.Helpers;
using APICORE.Services;
using APICORE.Services.Interfaces;
using System.Net;
using System.Threading.Tasks;
using System.Web.Http;

namespace API.Controllers
{
    [RoutePrefix("starstore")]
    public class StarStoreController : ApiController
    {
        public IStarStoreService StarStoreService { get; set; }
        public IClientService ClientService { get; set; }
        StarStoreController()
        {
            StarStoreService = new StarStoreService();
            ClientService = new ClientService();
        }

        [Authorize]
        [Route("product")]
        [HttpPost]
        [ValidateNullFilter]
        [ValidateModelStateFilter]
        public async Task<IHttpActionResult> Product([FromBody]Product product)
        {
            var status = await StarStoreService.SaveProduct(product);
            return Content(status, StatusCodeMessages.CustomMessage(status));
        }

        [Authorize]
        [Route("products")]
        [HttpGet]
        public IHttpActionResult Products()
        {
            var products = StarStoreService.GetProducts();
            return Ok(products);
        }

        [Authorize]
        [Route("buy")]
        [HttpPost]
        [ValidateNullFilter]
        [ValidateModelStateFilter]
        public async Task<IHttpActionResult> Buy([FromBody]TransactionRequest request)
        {
            var status = VerifyClient(request.Client_id) == HttpStatusCode.OK ?  await StarStoreService.SaveTransaction(request) : HttpStatusCode.BadRequest;
            return Content(status, StatusCodeMessages.CustomMessage(status));
        }

        [Authorize]
        [Route("history")]
        [HttpGet]
        public IHttpActionResult History()
        {
            var transactions = StarStoreService.GetHistory();
            return Ok(transactions);
        }

        [Authorize]
        [Route("history/{id}")]
        [HttpGet]
        public IHttpActionResult History(string id)
        {
            if (string.IsNullOrEmpty(id))
                return NotFound();

            if (VerifyClient(id) == HttpStatusCode.BadRequest)
                return Content(HttpStatusCode.BadRequest, StatusCodeMessages.CustomMessage(HttpStatusCode.BadRequest));

            var transactions = StarStoreService.HistoryByClientId(id);
            return Ok(transactions);
        }

        private HttpStatusCode VerifyClient(string id)
        {
            return ClientService.VerifyClient(id) == false ? HttpStatusCode.BadRequest : HttpStatusCode.OK;
        }
    }
}
