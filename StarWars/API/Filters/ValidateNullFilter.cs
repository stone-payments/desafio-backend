using System.Net;
using System.Net.Http;
using System.Web.Http.Controllers;
using System.Web.Http.Filters;

namespace API.Filters
{
    public class ValidateNullFilter : ActionFilterAttribute
    {
        public override void OnActionExecuting(HttpActionContext actionContext)
        {
            if (actionContext.ActionArguments.ContainsValue(null))
            {
                actionContext.Response = actionContext.Request
                    .CreateErrorResponse(HttpStatusCode.BadRequest,
                        "Error: null or invalid parameters.");
            }
        }
    }
}