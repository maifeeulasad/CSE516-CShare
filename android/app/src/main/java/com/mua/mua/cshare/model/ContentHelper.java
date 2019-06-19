package com.mua.mua.cshare.model;

import com.mua.mua.cshare.R;

public class ContentHelper {



    public static Content createContent(String location)
    {
        String format=location.substring(location.lastIndexOf('.')+1);
        int thumb=R.drawable.unknown_icon;
        if(format.toLowerCase().equals(".pdf"))
            thumb=R.drawable.pdf_icon;
        else if(format.toLowerCase().equals(".pdf"))
            thumb=R.drawable.image_icon;
        return new Content(location.substring(location.lastIndexOf('/')), location, thumb);
    }

}
