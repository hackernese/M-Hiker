using M_Hike.Components;
using M_Hike.Database;
using M_Hike.Database.Models;
using System.Diagnostics;

namespace M_Hike.Views;


[QueryProperty("reset", "reset")]
public partial class Dashboard : ContentPage
{


    //<ImageButton VerticalOptions = "Center" HorizontalOptions="End" Source="row.png" HeightRequest="25" WidthRequest="25">
    //            <ImageButton.Behaviors>
    //                <toolkit:IconTintColorBehavior TintColor = "White" ></ toolkit:IconTintColorBehavior>

    //            </ImageButton.Behaviors>
    //        </ImageButton>

    DatabaseHandler db;
    List<Hike> dbitems;
    string text;

    public bool reset
    {
        set
        {
            if (value)
            {
                // Reset the database extraction step
                Device.BeginInvokeOnMainThread(async () =>
                {
                    var data = await db.GetItemsAsync();
                    setvalue(data);
                });
            }
        }
    }

    public void setvalue(List<Hike> value)
    {
        dbitems = value;
        hikelist.ItemsSource = dbitems;

        if (dbitems.Count > 0)
            emptytitle.IsVisible = false;
        else
            emptytitle.IsVisible = true;
    }

	public Dashboard(DatabaseHandler db)
	{
		InitializeComponent();
        this.db = db;

        Device.BeginInvokeOnMainThread(async () =>
        {
            var data = await db.GetItemsAsync();
            setvalue(data);
        });

	}

    private async void Button_Clicked(object sender, EventArgs e)
    {
        // Navigate to the add hike page
        await Shell.Current.GoToAsync(nameof(AddHike), true);
    }

    private async void ImageButton_Clicked(object sender, EventArgs e)
    {
        // Delete all
        bool ret = await DisplayAlert("WARNING", "This action would permanently delete all hikes. Are you sure about that ?", "Cancel", "Yes");

        if (ret)
        {
            dbitems = await db.deleteall();
            hikelist.ItemsSource = dbitems;
            setvalue(dbitems);
        }

    }

    CustomBottomSheet sheet = new CustomBottomSheet();

    private async void hikelist_ItemSelected(object sender, EventArgs e)
    {

        if(hikelist.SelectedItem == null)
        {
            return;
        }

        ListView l = sender as ListView;
        Hike hike = l == null ? null : (l.SelectedItem as Hike);
         
        await sheet.build(hike, new Callback()
        {
            delete = async () =>
            {
                bool ret = await DisplayAlert("WARNING", "This action would permanently delete this hike. Are you sure about that ?", "Cancel", "Yes");

                if (ret)
                {
                    // Delete it
                    await db.DeleteItemAsync(hike);
                    dbitems = await db.GetItemsAsync();
                    hikelist.ItemsSource = dbitems;
                    setvalue(dbitems);

                }


            },
            edit = async () =>
            {
                await Shell.Current.GoToAsync(nameof(AddHike), true, new Dictionary<string, object> {
                    ["item"] = hike                   
                });
            }
        }).ShowAsync();
        
        sheet = new CustomBottomSheet();

        hikelist.SelectedItem = null;

    }
}