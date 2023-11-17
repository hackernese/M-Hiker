using Microsoft.Extensions.Logging;
using CommunityToolkit.Maui;
using M_Hike.Database;
using M_Hike.Views;
using The49.Maui.BottomSheet;

namespace M_Hike
{
    public static class MauiProgram
    {
        public static MauiApp CreateMauiApp()
        {
            var builder = MauiApp.CreateBuilder();
            builder
                .UseMauiApp<App>()
                .UseMauiCommunityToolkit()
                .UseBottomSheet()
                .ConfigureFonts(fonts =>
                {
                    fonts.AddFont("Roboto-Medium.ttf", "RobotoMedium");
                    fonts.AddFont("Roboto-Regular.ttf", "RobotoRegular");
                    fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
                    fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
                });

            // Inject database handler into the database
            builder.Services.AddTransient<MainPage>();
            builder.Services.AddTransient<AddHike>();
            builder.Services.AddTransient<Dashboard>();
            builder.Services.AddSingleton<DatabaseHandler>();

            #if DEBUG
		    builder.Logging.AddDebug();
            #endif

            return builder.Build();
        }
    }
}