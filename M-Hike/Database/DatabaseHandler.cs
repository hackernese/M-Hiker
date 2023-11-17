using M_Hike.Database.Models;
using SQLite;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace M_Hike.Database
{
    public class DatabaseHandler
    {

        SQLiteAsyncConnection db;

        public DatabaseHandler() { }
    
    
        async Task Init()
        {
            if (db is not null)
            return;

            db = new SQLiteAsyncConnection(Constants.DatabasePath, Constants.Flags);
            var result = await db.CreateTableAsync<Hike>();

        }


        public async Task<List<Hike>> deleteall()
        {
            await db.DropTableAsync<Hike>();

            db = null;

            return await this.GetItemsAsync();
        }

        public async Task<List<Hike>> GetItemsAsync()
        {
            await Init();
            return await db.Table<Hike>().ToListAsync();
        }

        public async Task<Hike> GetItemAsync(int id)
        {
            await Init();
            return await db.Table<Hike>().Where(i => i.ID == id).FirstOrDefaultAsync();
        }

        public async Task<int> SaveItemAsync(Hike item)
        {
            await Init();
            if (item.ID != 0)
            {
                return await db.UpdateAsync(item);
            }
            else
            {
                return await db.InsertAsync(item);
            }
        }

        public async Task<int> DeleteItemAsync(Hike item)
        {
            await Init();
            return await db.DeleteAsync(item);
        }
    }
}
