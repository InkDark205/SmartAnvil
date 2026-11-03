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


## Bản sửa lỗi màn hình cấu hình

Đã thay `renderBackground(...)` bằng `DrawContext.fill(...)` trong `SmartAnvilConfigScreen.java`. Trên 1.21.11, gọi nền có blur có thể gây lỗi `Can only blur once per frame` khi blur đã được yêu cầu trong cùng frame. Bản sửa dùng nền màu tĩnh, không gọi blur.

Lưu ý: mã hiện tại là nền tảng cấu hình/lập kế hoạch enchantment; chưa tự động thao tác slot/nhấn nút đe trong game.


### Giao diện thứ tự ưu tiên

Trong màn hình cấu hình, chọn **Edit enchantment priority**. Nhập từ khóa vào thanh tìm kiếm để lọc danh sách. Dùng nút `↑` hoặc `↓` để thay đổi thứ hạng toàn cục; việc lọc không thay đổi thứ tự đã lưu. Chọn **Reset to alphabetical** nếu muốn sắp xếp lại toàn bộ danh sách theo ID.


## Công tắc bật/tắt tổng

Màn hình cấu hình có công tắc `Smart Anvil automation: ON/OFF` ở dòng đầu tiên. Mặc định OFF và được lưu vào `config/smartanvil.json` bằng thuộc tính `enabled`. Nút Mode bên dưới chỉ đổi giữa SMART và PRIORITY, không thay thế công tắc tổng. Lưu ý: source hiện tại mới có phần cấu hình/UI, chưa có đầy đủ luồng tự động thao tác đe.
