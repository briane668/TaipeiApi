架構
 
   App架構採用 MVVM 的方式，並且使用到 databinding , LIveData observe 的方式來更新資料。
   使用單一 Activity 的方式，並且使用 navcontroller 來統一管理 Fragment 的跳轉
 
API處理
 
   使用 okhttp , retrofit , moshi 來處理 api  ,並且使用 singleton 的方式設計，保持Api物件只需要被創建一次即可，並且讓其他頁面可以簡單地呼叫來使用。
   將呼叫 Api 的 fun 宣告為 suspend ，使用 Coroutine 的方式來處理非同步請求，Coroutine使用起來不需要寫 call back 讓非同步的程式碼能夠更好管理與維護。
   使用 Glide 處理圖片下載，並放上 placeholder，讓等待下載時不會空白的一片。


  

 讀取動畫


   偵測 webview 的讀取進度，並且在讀取時放入 Progress bar，提升使用者體驗。
   一開始進入時Api使用ShimmerFrameLayout , Lottie animation 來讓畫面 loding 起來更有質感。
  

 Api 延伸功能
 
   根據 API 格式有觀察到其中包含地點的經緯度，所以決定在詳細頁面的地方加入了 Google map 地圖，讓單純枯燥的資料多了點實際的圖像，並且在目的地標上pin圖標，點下即可外開google map導航。
   也有觀察到 API 裏面包含了每個景點的分類標籤，所以我在景點的每個 view 下面，加入了屬於他們的標籤。
   另外根據景點全部的圖片以橫向滑動 recycle view 的方式呈現，讓使用者可以瀏覽。
