using System.Net;
using System.Net.Http;
using System.Web.Http.Controllers;
using System.Web.Http.Filters;

namespace APICORE.Filters
{
    public class ValidateModelStateFilter : ActionFilterAttribute
    {
        public override void OnActionExecuting(HttpActionContext actionContext)
        {
            if (!actionContext.ModelState.IsValid)
                actionContext.Response = actionContext.Request
                    .CreateErrorResponse(HttpStatusCode.BadRequest, actionContext.ModelState);
        }
    }
}
