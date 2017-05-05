using Microsoft.AspNet.Identity.EntityFramework;

namespace API.Auth
{
    public class AuthContext : IdentityDbContext<IdentityUser>
    {
        public AuthContext()
            : base("AuthDb")
        {
        }
    }
}