
using M_Hike.Database.Models;
using Microsoft.Extensions.Logging;
using The49.Maui.BottomSheet;
using Microsoft.Maui.Controls;

namespace M_Hike.Components;


public class Callback
{
    public Func<Task> edit { get; set; }
    public Func<Task> delete { get; set; }
}

public partial class CustomBottomSheet : BottomSheet
{

    Callback callback;
    Hike item = null;

	
    public CustomBottomSheet()
    {
        InitializeComponent();
        
        this.HasHandle = true;
        this.HasBackdrop = true;
        this.HandleColor = Color.FromRgba("#B6B6B6");
    }

    public CustomBottomSheet build(Hike hike, Callback callback)
	{
        this.item = hike;
        this.callback = callback;

        name.Text = item.namne;
        location.Text = item.location;
        ID.Text = item.ID.ToString ();
        lat.Text = item.lat.ToString();
        @long.Text = item.longtitude.ToString();
        date.Text = item.date.ToString();
        length.Text = item.length.ToString() + " " + item.units;
        difficulty.Text = item.level;
        parking.Text = item.parking ? "Yes" : "No"; 
        companions.Text = item.companions.ToString();
        description.Text = item.description.ToString();
        return this;
    }

    private async void Button_Clicked(object sender, EventArgs e)
    {
        // Edit this
        await this.DismissAsync();
        await this.callback.edit();
    }

    private async void Button_Clicked_1(object sender, EventArgs e)
    {
        // Delete the information of the hike
        await this.callback.delete();
        await this.DismissAsync();

    }
}