# Hướng dẫn build Smart Anvil

1. Cài JDK 21 và Gradle tương thích với Fabric Loom đã khai báo.
2. Giải nén thư mục `SmartAnvil-InkDark`.
3. Mở terminal tại thư mục có `build.gradle`.
4. Chạy `gradle build` (Windows có thể chạy trong PowerShell/CMD; Linux/macOS chạy trong terminal).
5. Nếu build thành công, kiểm tra JAR trong `build/libs/`.
6. Chỉ thử trong profile Minecraft 1.21.11 + Fabric, có Fabric API và Mod Menu đúng phiên bản.

## Trạng thái hiện tại
Đây là gói source nền có entrypoint, màn hình cấu hình, lưu cấu hình và các primitive lập kế hoạch/kiểm tra an toàn. Chưa có vòng lặp tự động thao tác đầy đủ với inventory/đe, chưa kiểm thử gameplay và chưa có JAR đã xác minh. Không nên coi gói này là bản mod tự động ghép hoàn chỉnh.

## Build bằng GitHub Actions (không cần cài Gradle trên điện thoại)
1. Tải lên repository toàn bộ nội dung bên trong thư mục `SmartAnvil-InkDark` (bao gồm `.github/workflows/build.yml`), không chỉ tải file ZIP.
2. Mở tab **Actions** của repository và chọn workflow **Build SmartAnvil**.
3. Nếu workflow chạy thành công, mở lần chạy mới nhất và tải artifact `SmartAnvil-1.21.11-Fabric` ở cuối trang.
4. Đây chỉ xác nhận build thành công. Tính năng tự động thao tác anvil vẫn cần được triển khai và kiểm thử riêng.
