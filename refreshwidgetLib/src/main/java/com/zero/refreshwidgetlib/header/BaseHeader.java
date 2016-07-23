package com.zero.refreshwidgetlib.header;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * BaseHeader
 * @author linzewu
 * @date 16-7-5
 */
public abstract class BaseHeader extends RelativeLayout implements HeaderInterface{
    
    protected float mPercent;

    @Override
    public float getPercent() {
        return mPercent;
    }

    @Override
    public void setPercent(float percent) {
        mPercent = percent;
    }

    public BaseHeader(Context context) {
        super(context);
    }

    public BaseHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh(float percent) {
        this.mPercent = percent;
    }

    @Override
    public void onRefreshIng() {
        
    }

    @Override
    public void onReleaseToRefresh() {
        
    }
}
