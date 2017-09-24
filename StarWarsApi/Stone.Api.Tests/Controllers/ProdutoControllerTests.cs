using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using Stone.Api.Controllers;
using Stone.Api.Models;
using Stone.Api.Services;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Stone.Api.Tests.Controllers
{
    [TestClass]
    public class ProdutoControllerTests
    {
        private Mock<IProdutoService> _mock;
        private IList<Produto> _produtos;

        [TestInitialize]
        public void SetUp()
        {
            _produtos = new List<Produto>();
            _produtos.Add(new Produto()
            {
                title = "Blusa do Imperio",
                price = 7990,
                ZipCode = "78993-000",
                Seller = "João da Silva",
                ThumbnailHd = "https://cdn.awsli.com.br/600x450/21/21351/produto/3853007/f66e8c63ab.jpg",
                Date = "26/11/2015"
            });
            _produtos.Add(new Produto()
            {
                title = "Blusa Han Shot First",
                price = 7990,
                ZipCode = "13500-110",
                Seller = "Joana",
                ThumbnailHd = "https://cdn.awsli.com.br/1000x1000/21/21351/produto/7234148/55692a941d.jpg",
                Date = "26/11/2015"
            });
            _produtos.Add(new Produto()
            {
                title = "Sabre de luz",
                price = 150000,
                ZipCode = "13537-000",
                Seller = "Mario Mota",
                ThumbnailHd = "http://www.obrigadopelospeixes.com/wp-content/uploads/2015/12/kalippe_lightsaber_by_jnetrocks-d4dyzpo1-1024x600.jpg",
                Date = "20/11/2015"
            });
        }

        [TestMethod]
        public async Task ListarTodosProdutos()
        {
            _mock = new Mock<IProdutoService>();
            _mock.Setup(x => x.ListarAsync())
                .Returns(Task.FromResult(_produtos));

            var controller = new ProdutoController(_mock.Object);
            var result = await controller.ListarProdutos();
            Assert.AreEqual(result.Count(), _produtos.Count());
        }
    }
}
