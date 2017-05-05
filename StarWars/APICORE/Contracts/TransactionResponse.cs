using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace APICORE.Contracts
{
    public class TransactionResponse
    {
        public string Client_id { get; set; }
        public string Purchase_id { get; set; }
        public int Value { get; set; }
        public string Date { get; set; }
        public string Card_number { get; set; }

    }
}
