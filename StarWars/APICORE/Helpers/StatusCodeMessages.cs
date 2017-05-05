using System.Net;

namespace APICORE.Helpers
{
    public static class StatusCodeMessages
    {
        public static string CustomMessage(HttpStatusCode statusCode)
        {
            switch (statusCode)
            {
                case HttpStatusCode.OK:
                    return "Successful Operation";
                case HttpStatusCode.InternalServerError:
                    return "Operation failed";
                case HttpStatusCode.BadRequest:
                    return "Invalid Parameters";
                default:
                    return "Operation Failed";

            }
        }
    }
}
