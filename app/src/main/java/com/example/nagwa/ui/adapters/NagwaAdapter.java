package com.example.nagwa.ui.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nagwa.R;
import com.example.nagwa.models.NagwaModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NagwaAdapter extends RecyclerView.Adapter<NagwaAdapter.Holder> {
    private static final String TAG = "mustafa";
    private List<NagwaModel> data = new ArrayList<>();
    private DownloadManager downloadManager;
    private Context context;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nagwa_item, parent, false));
    }

    public NagwaAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NagwaModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.type.setText(data.get(position).getType());

//        start download when button clicked
        holder.download.setOnClickListener(view -> {
            String ty;
            if (data.get(position).getType().equals("PDF")) {
                ty = ".pdf";
            } else {
                ty = ".mp4";
            }
            holder.download.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.VISIBLE);
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(data.get(position).getUrl());
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, data.get(position).getName() + ty);
            long aLong = downloadManager.enqueue(request);
            holder.progressBar.setProgress(3);
            holder.downloadStatus.setText("Downloading...");
            holder.downloadStatus.setVisibility(View.VISIBLE);


//            update progress bar
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    int progress = 0;
                    boolean isDownloadFinished = false;
                    while (!isDownloadFinished) {
                        Cursor cursor = downloadManager.query(new DownloadManager.Query().setFilterById(aLong));
                        if (cursor.moveToFirst()) {
                            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                            switch (downloadStatus) {
                                case DownloadManager.STATUS_RUNNING:
                                    long totalBytes = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                                    if (totalBytes > 0) {
                                        long downloadedBytes = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                                        progress = (int) (downloadedBytes * 100 / totalBytes);
                                        holder.progressBar.setProgress(progress);
                                    }

                                    break;
                                case DownloadManager.STATUS_SUCCESSFUL:
                                    progress = 100;
                                    holder.progressBar.setProgress(progress);
                                    holder.progressBar.setVisibility(View.INVISIBLE);
                                    holder.downloadStatus.setText("Download complete");
                                    isDownloadFinished = true;
                                    break;
                                case DownloadManager.STATUS_PAUSED:
                                case DownloadManager.STATUS_PENDING:
                                    break;
                                case DownloadManager.STATUS_FAILED:
                                    isDownloadFinished = true;
                                    break;
                            }
                        }
                    }
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView name, type, downloadStatus;
        Button download;
        ProgressBar progressBar;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            type = itemView.findViewById(R.id.item_type);
            download = itemView.findViewById(R.id.item_download_but);
            progressBar = itemView.findViewById(R.id.item_progressbar);
            downloadStatus = itemView.findViewById(R.id.item_download_status);
        }
    }
}
