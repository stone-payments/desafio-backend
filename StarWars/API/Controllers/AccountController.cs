using API.Auth;
using API.Filters;
using APICORE.Contracts;
using APICORE.Entities;
using APICORE.Filters;
using APICORE.Services;
using APICORE.Services.Interfaces;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System.Threading.Tasks;
using System.Web.Http;

namespace API.Controllers
{
    [RoutePrefix("account")]
    public class AccountController : ApiController
    {
        private AuthRepository _repo = null;
        public IClientService service { get; set; }

        public AccountController()
        {
            _repo = new AuthRepository();
            service = new ClientService();
        }

        [Route("register")]
        [HttpPost]
        [ValidateNullFilter]
        [ValidateModelStateFilter]
        public async Task<IHttpActionResult> Register([FromBody]UserRequest user)
        {
            var userModel = new UserModel
            {
                UserName = user.UserName,
                Email = user.Email,
                Password = user.Password,
                ConfirmPassword = user.ConfirmPassword
            };

            IdentityResult result = await _repo.RegisterUser(userModel);

            IHttpActionResult errorResult = GetErrorResult(result);

            if (errorResult != null)
            {
                return errorResult;
            }

            UserResponse userResponse = await MappingToUserResponse(userModel);

            if (userResponse == null)
            {
                return InternalServerError();
            }

            await RegisterClient(userResponse);

            return Ok(userResponse);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                _repo.Dispose();
            }

            base.Dispose(disposing);
        }

        private IHttpActionResult GetErrorResult(IdentityResult result)
        {
            if (result == null)
            {
                return InternalServerError();
            }

            if (!result.Succeeded)
            {
                if (result.Errors != null)
                {
                    foreach (string error in result.Errors)
                    {
                        ModelState.AddModelError("", error);
                    }
                }

                if (ModelState.IsValid)
                {
                    return BadRequest();
                }

                return BadRequest(ModelState);
            }

            return null;
        }

        private async Task<UserResponse> MappingToUserResponse(UserModel userModel)
        {
            IdentityUser userResult = await _repo.FindUser(userModel.UserName, userModel.Password);
            return new UserResponse
            {
                UserName = userResult.UserName,
                Email = userResult.Email,
                ClientId = userResult.Id
            };
        }
        private async Task RegisterClient(UserResponse user)
        {
            var client = new Client
            {
                ClientId = user.ClientId,
                ClientName = user.UserName
            };
            await service.CreateClient(client);
        }
    }
}