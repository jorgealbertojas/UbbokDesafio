package com.example.jorge.myapplication.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jorge.myapplication.R;
import com.example.jorge.myapplication.data.source.cloud.movie.ListMovies;
import com.example.jorge.myapplication.data.source.cloud.movie.MoviesServiceImpl;
import com.example.jorge.myapplication.data.source.cloud.movie.model.Movies;
import com.example.jorge.myapplication.detailMovies.DetailMoviesActivity;
import com.example.jorge.myapplication.util.EndlessRecyclerViewScrollListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import static com.example.jorge.myapplication.util.PathForApi.URL_IMAGE;
import static com.example.jorge.myapplication.util.PathForApi.URL_SIZE_W154;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


public class MoviesFragment extends Fragment implements MoviesContract.View{

    public static String EXTRA_MOVIE = "EXTRA_MOVIE";

    private MoviesAdapter mListAdapter;
    private RecyclerView mRecyclerView;
    private static MoviesContract.UserActionsListener mActionsListener;
    private MoviesContract.View mMoviesContract;

    LinearLayoutManager mLinearLayoutManager;
    private EndlessRecyclerViewScrollListener mScrollListener;

    private static Bundle mBundleRecyclerViewState;
    private final String KEY_RECYCLER_STATE = "RECYCLER_VIEW_STATE";
    private final String KEY_ADAPTER_STATE = "ADAPTER_STATE";
    private Parcelable mListState;

    private ListMovies<Movies> mListMovies;

    private static Boolean mPopular = false;

    /**
     * Constructor
     */
    public MoviesFragment() {
    }

    /**
     * Constructor with new Instance
     * @return
     */
    public static MoviesFragment newInstance(boolean popular) {
        mPopular = popular;
        return new MoviesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMoviesContract = this;
        if (savedInstanceState == null) {
            mListAdapter = new MoviesAdapter(new ListMovies<Movies>(), mItemListener);
            mActionsListener = new MoviesPresenter(new MoviesServiceImpl(), this);
            mActionsListener.loadingMovies(mPopular);
            mActionsListener.start();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null) {
            mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mListMovies = (ListMovies<Movies>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movies, container, false);

        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mActionsListener.loadingMovies(mPopular);
            }
        });


        if (savedInstanceState== null){
            initRecyclerView(root);
            mBundleRecyclerViewState = new Bundle();
            Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        }else{
            initRecyclerView(root);
            mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mListMovies = (ListMovies<Movies>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);
            mListAdapter = new MoviesAdapter(mListMovies,mItemListener);
            mRecyclerView.setAdapter(mListAdapter);

        }

        return root;
    }

    /**
     * Init RecyclerView fro show list car
     * @param root
     */
    private void initRecyclerView(View root){
        mRecyclerView= (RecyclerView) root.findViewById(R.id.rv_movies_list);
        mRecyclerView.setAdapter(mListAdapter);

        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mActionsListener = new MoviesPresenter(new MoviesServiceImpl(), mMoviesContract);
                mActionsListener.loadingMovies(mPopular);
                mActionsListener.start();

            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);

    }

    @Override
    public void onPause() {
        super.onPause();

        mBundleRecyclerViewState = new Bundle();
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mListMovies = (ListMovies<Movies>) mListAdapter.mMovies;
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
        mBundleRecyclerViewState.putSerializable(KEY_ADAPTER_STATE, (Serializable) mListMovies);
    }

    @Override
    public void setLoading(final boolean isActive) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(isActive);
            }
        });
    }

    @Override
    public void showMovies(ListMovies<Movies> moviesList) {
        if (mRecyclerView.getAdapter().getItemCount() > 0) {
            moviesList.results.addAll(0,(List<Movies>) mListAdapter.mMovies.results);
        }
        mListAdapter.replaceData(moviesList);
    }


    @Override
    public void setPresenter(MoviesContract.UserActionsListener presenter) {
        mActionsListener = checkNotNull(presenter);

    }

    /**
     * Adapter for fill the list of the movies
     */
    private class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

        private ListMovies<Movies> mMovies;
        private ItemListener mItemListener;

        public MoviesAdapter(ListMovies<Movies> movies, ItemListener itemListener) {
            setList(movies);
            mItemListener = itemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.item_movies, parent, false);

            return new ViewHolder(noteView, mItemListener);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            Movies movies = mMovies.results.get(position);

            int imageDimension =
                    (int) viewHolder.moviesImage.getContext().getResources().getDimension(R.dimen.card_height);

            int imageWight =
                    (int) viewHolder.moviesImage.getContext().getResources().getDimension(R.dimen.image_wight);

            Picasso.with(viewHolder.moviesImage.getContext())
                    .load(URL_IMAGE + URL_SIZE_W154 + movies.getPosterPath())
                    .resize(imageWight,imageDimension)
                    .onlyScaleDown()
                    .error(R.drawable.ic_error_black_24dp)
                    .into(viewHolder.moviesImage);

            viewHolder.title.setText(movies.getTitle());
            viewHolder.release_date.setText(movies.getReleaseDate());
            viewHolder.voteAverage.setText(movies.getVoteAverage());
        }

        public void replaceData(ListMovies<Movies> movies) {
            setList(movies);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }



        private void setList(ListMovies<Movies> movies) {
            mMovies = movies;
        }

        @Override
        public int getItemCount() {
            if (mMovies.results != null) {
                return mMovies.results.size();
            }else{
                return 0;
            }
        }

        public Movies getItem(int position) {
            return mMovies.results.get(position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ImageView moviesImage;
            public TextView title;
            public TextView voteAverage;
            public TextView release_date;
            private ItemListener mItemListener;

            public ViewHolder(View itemView, ItemListener listener) {
                super(itemView);
                mItemListener = listener;
                title = (TextView) itemView.findViewById(R.id.tv_Title);
                voteAverage = (TextView) itemView.findViewById(R.id.tv_vote_average);
                moviesImage = (ImageView) itemView.findViewById(R.id.iv_imageMovie_main);
                release_date = (TextView) itemView.findViewById(R.id.tv_release_date);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                Movies movies = getItem(position);
                mItemListener.onMoviesClick(movies);

                Intent intent = new Intent(getContext(), DetailMoviesActivity.class);
                intent.putExtra(EXTRA_MOVIE, movies);
                startActivity(intent);


            }
        }
    }

    /**
     * Listener which car click
     */
    ItemListener mItemListener = new ItemListener() {
        @Override
        public void onMoviesClick(Movies clickedMovie) {
            mActionsListener.loadingMovies(mPopular);
        }


    };

    public interface ItemListener {

        void onMoviesClick(Movies clickedMovie);
    }
}
