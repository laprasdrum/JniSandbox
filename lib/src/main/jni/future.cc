#include <iostream>
#include <unistd.h>     // sleep
#include <thread>
#include <future>
using namespace std;
int
foo (std::promise<int>& pi, int& num) {
        cout << "execute: " << __PRETTY_FUNCTION__ << endl;
        for (int i = 0; i < 10; i++) {
                num++;
                sleep (1);
        }
        pi.set_value(num);
}
extern "C" int countThread()
{
        try {
                std::promise<int> pi;
                int     num = 0;
                std::thread t1(foo, std::ref(pi), std::ref(num));       // スレッドを開始
                cout << num << endl;
                std::future<int> f0 = pi.get_future();                  // 非同期オブジェクトを受け取る宣言
                int r = f0.get();                                       // 同期でタスク処理を待つ
                cout << r << endl;                                      // 結果を表示
                t1.join();                                              // スレッドの終了を待つ
                return r;
        } catch (std::exception& e) {
                cerr << e.what() << endl;
        }
        return 0;
}
