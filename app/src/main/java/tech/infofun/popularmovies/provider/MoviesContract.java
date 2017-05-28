package tech.infofun.popularmovies.provider;

import android.net.Uri;

/**
 * Created by tfbarbosa on 28/05/17.
 */
public final class MoviesContract {

    public static final String AUTHORITY = "tech.infofun.popularmovies.provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    public static final String MOVIE_PATH = "movie";

    public static final class Movie{

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" +
                "vnd.tech.infofun.popularmovies.provider/movie";

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" +
                "vnd.tech.infofun.popularmovies.provider/movie";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, MOVIE_PATH);
        public static final String _ID = "_id";
        public static final String MOVIE_ID = "movie_id";
        public static final String TITLE = "title";
        public static final String POSTER = "poster";
        public static final String DESCRIPTION = "description";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String RELEASE_DATE = "release_date";
        public static final String BACKDROP = "backdrop";
    }
}
