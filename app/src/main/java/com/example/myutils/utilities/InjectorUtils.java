package com.example.myutils.utilities;

import android.content.Context;

import com.libramotors.libmot.data.Repository;
import com.libramotors.libmot.data.network.NetworkDataSource;
import com.libramotors.libmot.ui.viewModels.BookingStatusViewModelFactory;
import com.libramotors.libmot.ui.viewModels.BusHireViewModelFactory;
import com.libramotors.libmot.ui.viewModels.ComplaintViewModelFactory;
import com.libramotors.libmot.ui.viewModels.CouponViewModelFactory;
import com.libramotors.libmot.ui.viewModels.FeedsViewModelFactory;
import com.libramotors.libmot.ui.viewModels.LoginViewModelFactory;
import com.libramotors.libmot.ui.viewModels.MainViewModelFactory;
import com.libramotors.libmot.ui.viewModels.PaymentViewModelFactory;
import com.libramotors.libmot.ui.viewModels.PostBookingViewModelFactory;
import com.libramotors.libmot.ui.viewModels.ProfileViewModelFactory;
import com.libramotors.libmot.ui.viewModels.SignUpViewModelFactory;
import com.libramotors.libmot.ui.viewModels.TripHistoryViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for Sunshine
 */
public class
InjectorUtils {

    private static Repository provideRepository(Context context) {
        AppExecutors executors = AppExecutors.getInstance();
        NetworkDataSource networkDataSource =
                NetworkDataSource.getInstance(executors);
        return Repository.getInstance(networkDataSource, executors, context);
    }

    public static NetworkDataSource provideNetworkDataSource() {
        AppExecutors executors = AppExecutors.getInstance();
        return NetworkDataSource.getInstance(executors);
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new MainViewModelFactory(repository);
    }

    public static LoginViewModelFactory provideLoginViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new LoginViewModelFactory(repository);
    }
    public static ProfileViewModelFactory provideProfileViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new ProfileViewModelFactory(repository);
    }

    public static PostBookingViewModelFactory providePostBookingViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new PostBookingViewModelFactory(repository);
    }

    public static PaymentViewModelFactory providePaymentViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new PaymentViewModelFactory(repository);
    }

    public static BusHireViewModelFactory provideBusHireViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new BusHireViewModelFactory(repository);
    }

    public static BookingStatusViewModelFactory provideBookingStatusViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new BookingStatusViewModelFactory(repository);
    }

    public static TripHistoryViewModelFactory provideTripHistoryViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new TripHistoryViewModelFactory(repository);
    }

    public static SignUpViewModelFactory provideSignUpViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new SignUpViewModelFactory(repository);
    }

    public static ComplaintViewModelFactory provideComplaintViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new ComplaintViewModelFactory(repository);
    }


    public static FeedsViewModelFactory provideFeedViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new FeedsViewModelFactory(repository);
    }


    public static CouponViewModelFactory provideCouponViewModelFactory(Context context) {
        Repository repository = provideRepository(context.getApplicationContext());
        return new CouponViewModelFactory(repository);
    }

}
