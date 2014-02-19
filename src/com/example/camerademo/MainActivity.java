package com.example.camerademo;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.util.PrintStreamPrinter;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.joya.matchthesunset.R;

public class MainActivity extends Activity implements SurfaceHolder.Callback{

	
	Button btn_capture;
	Camera camera1;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	TextView txt,time_txt;
	 File file;
	 String time,filena;
	  private Bitmap finalBitmap = null;
	public static boolean previewing = false;
	
	static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	
	
    @SuppressWarnings("deprecation")
	@SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
        
        String fontPath = "Urban.ttf";
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		
        
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView) findViewById(R.id.hi);
        txt=(TextView) findViewById(R.id.text);
        time_txt=(TextView) findViewById(R.id.time);
        txt.setTypeface(tf);
        time_txt.setTypeface(tf);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    btn_capture = (Button) findViewById(R.id.button1);
    
       time=GetUTCdatetimeAsString();
       filena=time;
       time_txt.setText("UTC Time : "+time);
       
        time=time.substring(11, 13);
        Log.v("time", time);
        System.out.println(time);
        
      //  if(time.equalsIgnoreCase("07"))
        {
        	
        	//txt.setText("Hey south america, sun is rising in australia and japan, take a photo of the sunset NOW");
        }
      /*  else if(time.equalsIgnoreCase("10"))
        {
        	txt.setTextColor(213456);
        	txt.setText("Sun is rising in china! message is to east coast united states TAKE A SUNSET PHOTO NOW");
        }
        else if(time.equalsIgnoreCase("13"))
        {
        	txt.setTextColor(213456);
        	txt.setText("Sun is rising in South Africa Pakistan Madagascar, take a SUNSET MATCH NOW!");
        }
        else
        {
        	//txt.setVisibility(View.GONE);
        }*/
        	

    if(!previewing){

            camera1 = Camera.open();
            if (camera1 != null){
                try {
                    camera1.setDisplayOrientation(90);
                    camera1.setPreviewDisplay(surfaceHolder);
                    camera1.startPreview();
                    previewing = true;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    
  
	
	
    btn_capture.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(camera1 != null)
                {
                    camera1.takePicture(myShutterCallback, myPictureCallback_RAW, myPictureCallback_JPG);

                }
            }
        });
    }

    ShutterCallback myShutterCallback = new ShutterCallback(){

        public void onShutter() {
            // TODO Auto-generated method stub
        }};

    PictureCallback myPictureCallback_RAW = new PictureCallback(){

        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
        }};

    PictureCallback myPictureCallback_JPG = new PictureCallback(){

        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
            Bitmap bitmapPicture = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);

            finalBitmap = Bitmap.createBitmap(bitmapPicture, 0, 0, bitmapPicture.getWidth(), bitmapPicture.getHeight(), null, true);

            new Thread(new Runnable() { 
                public void run(){

         
                	uploadImage();

                }
        }).start();
            
            
               
            
            
            
            
            
            camera1.setDisplayOrientation(90);
            try {
				camera1.setPreviewDisplay(surfaceHolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            camera1.startPreview();
            previewing = true;
        }};

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        // TODO Auto-generated method stub
        if(previewing){
            camera1.stopPreview();
            previewing = false;
        }

        if (camera1 != null){
            try {
                camera1.setPreviewDisplay(surfaceHolder);
                camera1.startPreview();
                previewing = true;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

            camera1.stopPreview();
            camera1.release();
            camera1 = null;
            previewing = false;

    }
    public void gotowebpage(View v)
    {
    	startActivity(new Intent(getApplicationContext(), WebActivity.class));
    }
    
   
   void uploadImage()
   {
		File sdCardDirectory = Environment.getExternalStorageDirectory();

		// Next, create your specific file for image storage:

	 file = new File(sdCardDirectory, "test.png");

		// After that, you just have to write the Bitmap thanks to its method
		// compress such as:

		boolean success = false;

		// Encode the file as a PNG image.
		FileOutputStream outStream;
		try {

			outStream = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.PNG,
					100, outStream);
			/* 100 to keep full quality of the image */

			outStream.flush();
			outStream.close();
			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Finally, just deal with the boolean result if needed. Such as:

		if (success) {

		} else {
			Toast.makeText(getApplicationContext(),
					"Error during image saving", Toast.LENGTH_LONG).show();
		}

		
		uploadFile("");
   }
  @Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	
	//	com.facebook.AppEventsLogger.activateApp(getApplicationContext(), "1378212579084638");
	

	  if(!previewing){

          camera1 = Camera.open();
          if (camera1 != null){
              try {
                  camera1.setDisplayOrientation(90);
                  camera1.setPreviewDisplay(surfaceHolder);
                  camera1.startPreview();
                  previewing = true;
              } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
          }
      }
  
}
   public void uploadFile(String sourceFileUri) {
	      
	      
	   
	   
	   
	   String fileName = sourceFileUri;
	   
       HttpURLConnection conn = null;
       DataOutputStream dos = null; 
       String lineEnd = "\r\n";
       String twoHyphens = "--";
       String boundary = "*****";
       int bytesRead, bytesAvailable, bufferSize;
       byte[] buffer;
       int maxBufferSize = 1 * 1024 * 1024;
       File sourceFile = new File(sourceFileUri);
        
       if (false) {
            
          
             
       
         
         
       }
       else
       {
            try {
                 
                  // open a URL connection to the Servlet
            	 FileInputStream fileInputStream = new FileInputStream("/mnt/sdcard/test.png");
	              // URL url = new URL("http://www.groupshot.us/imageupload.php");
	               URL url = new URL("http://www.solsisters.groupshot.us/sunset/upload.php");
                 
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", "1.png");
                 
                dos = new DataOutputStream(conn.getOutputStream());
       
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename="+System.currentTimeMillis()+".png" + lineEnd);
                 
                dos.writeBytes(lineEnd);
       
                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
       
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
       
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize); 
                   
                while (bytesRead > 0) {
                     
                  dos.write(buffer, 0, bufferSize);
                  bytesAvailable = fileInputStream.available();
                  bufferSize = Math.min(bytesAvailable, maxBufferSize);
                  bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                   
                 }
       
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
       
                // Responses from the server (code and message)
              int  serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                  
                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);
                 
                if(serverResponseCode == 200){
                     
                    runOnUiThread(new Runnable() {
                         public void run() {
                             String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                   +" F:/wamp/wamp/www/uploads";
                             Toast.makeText(MainActivity.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                         }
                     });               
                }   
                 
                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();
                  
           } catch (MalformedURLException ex) {
                
              
               ex.printStackTrace();
                
               runOnUiThread(new Runnable() {
                   public void run() {
                            }
               });
                
               Log.e("Upload file to server", "error: " + ex.getMessage(), ex); 
           } catch (Exception e) {
                
              
               e.printStackTrace();
                
               runOnUiThread(new Runnable() {
                   public void run() {
                               Toast.makeText(MainActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                   }
               });
               Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e); 
           }
          
         
            
        } // End else block
      }

	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	/*   
	   
	     

	      HttpURLConnection conn = null;
	      DataOutputStream dos = null; 
	      String lineEnd = "\r\n";
	      String twoHyphens = "--";
	      String boundary = "*****";
	      int bytesRead, bytesAvailable, bufferSize;
	      byte[] buffer;
	      int maxBufferSize = 1 * 1024 * 1024;
	      
	      System.out.println("11");
	     
	           try {
	        	   System.out.println("33");
	                 // open a URL connection to the Servlet
	               FileInputStream fileInputStream = new FileInputStream("/mnt/sdcard/test.png");
	              // URL url = new URL("http://www.groupshot.us/imageupload.php");
	               URL url = new URL("http://192.168.1.215:81/ambrish/PHP/upload.php");
	               // Open a HTTP  connection to  the URL
	               conn = (HttpURLConnection) url.openConnection();
	               conn.setDoInput(true); // Allow Inputs
	               conn.setDoOutput(true); // Allow Outputs
	               conn.setUseCaches(false); // Don't use a Cached Copy
	               conn.setRequestMethod("POST"); 
	               conn.setRequestProperty("Connection", "Keep-Alive");
	               conn.setRequestProperty("ENCTYPE", "multipart/form-data");
	               conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	               conn.setRequestProperty("uploaded_file", "hello.png");
	                
	               dos = new DataOutputStream(conn.getOutputStream());
	      
	               dos.writeBytes(twoHyphens + boundary + lineEnd);
	               dos.writeBytes("Content-Disposition: form-data; name=file;filename=1.png" + lineEnd);
	               System.out.println("44");
	               dos.writeBytes(lineEnd);
	      
	               // create a buffer of  maximum size
	               bytesAvailable = fileInputStream.available();
	      
	               bufferSize = Math.min(bytesAvailable, maxBufferSize);
	               buffer = new byte[bufferSize];
	      
	               // read file and write it into form...
	               bytesRead = fileInputStream.read(buffer, 0, bufferSize); 
	                  
	               while (bytesRead > 0) {
	            	   System.out.println("55");
	                 dos.write(buffer, 0, bufferSize);
	                 bytesAvailable = fileInputStream.available();
	                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
	                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
	                  
	                }
	      
	               // send multipart form data necesssary after file data...
	               dos.writeBytes(lineEnd);
	               dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	            int   serverResponseCode = conn.getResponseCode();
	               String serverResponseMessage = conn.getResponseMessage();
	                 
	               Log.i("uploadFile", "HTTP Response is : "
	                       + serverResponseMessage + ": " + serverResponseCode);
	               // Responses from the server (code and message)
	               System.out.println("66");
	                   runOnUiThread(new Runnable() {
	                        public void run() {
	                             
	                           
	                             
	                        
	                            Toast.makeText(MainActivity.this, "File Upload Complete.",
	                                         Toast.LENGTH_SHORT).show();
	                        }
	                    });               
	              
	                
	               //close the streams //
	               fileInputStream.close();
	               dos.flush();
	               dos.close();
	               System.out.println("66");
	          } catch (MalformedURLException ex) {
	               
	             
	              ex.printStackTrace();
	               
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                     Toast.makeText(MainActivity.this, "MalformedURLException",
	                                                          Toast.LENGTH_SHORT).show();
	                  }
	              });
	               
	              Log.e("Upload file to server", "error: " + ex.getMessage(), ex); 
	          } catch (Exception e) {
	               
	          
	              e.printStackTrace();
	               
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                      Toast.makeText(MainActivity.this, "Got Exception : see logcat ",
	                              Toast.LENGTH_SHORT).show();
	                  }
	              });
	              Log.e("Upload file to server Exception", "Exception : "
	                                               + e.getMessage(), e); 
	          }
	         
	          return 1;
	           
	        // End else block
	     } 
	*/  
   public static String GetUTCdatetimeAsString()
	{
	    final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
	    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
	    final String utcTime = sdf.format(new Date());

	    return utcTime;
	}
   /*
    10 UTC - sun is rising in china! message is to east coast united states TAKE A SUNSET PHOTO NOW
13 UTC sun is rising in South Africa Pakistan Madagascar, take a SUNSET MATCH NOW!
    */
}
