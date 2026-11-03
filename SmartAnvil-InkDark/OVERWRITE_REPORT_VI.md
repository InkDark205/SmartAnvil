# Báo cáo ghi đè Smart Anvil

## File đã sửa
- `src/main/java/inkdark/smartanvil/gui/SmartAnvilConfigScreen.java`: bỏ `renderBackground(...)` khỏi `render(...)`, dùng `context.fill(...)` để tránh gọi blur lần thứ hai trong một frame.
- `README.md`: ghi rõ bản sửa và giới hạn chức năng hiện tại.
- `BUILD_GUIDE_VI.md`: bổ sung hướng dẫn và nguyên nhân crash.

## File giữ nguyên
- `gradle.properties`: Minecraft 1.21.11, Yarn `1.21.11+build.6`, Loader `0.19.3`, Fabric API `0.141.4+1.21.11`, Loom `1.17.13`, Java 21.
- Các file config, optimizer, safety, entrypoint và metadata không có lỗi được xác nhận trong crash mới nhất nên không thay đổi.

## Giới hạn xác minh
Môi trường hiện tại không có lệnh Gradle và không thể chạy build Gradle offline. Vì vậy gói này là source project đã sửa; chưa tạo JAR mới và không được xem JAR cũ là bản đã vá. Chức năng tự động thao tác anvil vẫn chưa được triển khai/kiểm chứng.
