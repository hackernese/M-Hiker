using M_Hike.Database;
using M_Hike.Views;

namespace M_Hike
{
    public partial class MainPage : ContentPage
    {

        DatabaseHandler database;

        public MainPage(DatabaseHandler db)
        {
            InitializeComponent();
            this.database = db;


            Device.BeginInvokeOnMainThread(async () =>await Shell.Current.GoToAsync(nameof(Dashboard), true));
        }

    }
}