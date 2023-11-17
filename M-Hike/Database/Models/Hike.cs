using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SQLite;




namespace M_Hike.Database.Models
{
       
    //"created     TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
    //"modified    TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
    public class Hike
    {

        public Hike buildparams(string namne, string location, double lat, double longtitude, string date, string units, string level, int length, bool parking, int companions, string description)
        {
            this.namne = namne;
            this.location = location;
            this.lat = lat;
            this.longtitude = longtitude;
            this.date = date;
            this.units = units;
            this.level = level;
            this.length = length;
            this.parking = parking;
            this.companions = companions;
            this.description = description;
            return this;
        }

        [PrimaryKey, AutoIncrement]
        public int ID { get; set; }
        public string namne { get; set; }
        public string location { get; set; }
        public double lat { get; set; }
        public double longtitude { get; set; }
        public string date { get; set; }
        public string units { get; set; }
        public string level { get; set; }
        public int length { get; set; }
        public bool parking { get; set; }
        public int companions { get; set; }
        public string description { get; set; }


        public Hike Clone()
        {
            return this.Clone();
        }

    }
}
