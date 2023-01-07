package com.zeewain.base.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.zeewain.base.R;


@GlideModule
public class MyGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context));
        builder.setDefaultRequestOptions(
                new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL));
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}
