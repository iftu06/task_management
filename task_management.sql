
CREATE DATABASE task_management;

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ADMIN'),
(2, 'USER');


INSERT INTO `user` (`id`, `active`, `email`, `mobile_no`, `password`, `user_name`, `first_name`, `last_name`) VALUES
(13, 1, 'iftekharuddin52@gmail.com', '01635482457', '$2a$10$x14kRbwuPkix.n6du64dXu3pzgXX7/amA8ajc0FS/qlG2oTWKy.rO', 'iftu06', NULL, NULL),
(17, 1, 'iftu_hai07@yahoo.com', '01635482457', '$2a$10$0y/1FGFFeLSpI3aDGaCtu.a3mucQGo37Z2BnwL9Duaz1HTs7irS6a', 'iftekhar', NULL, NULL),
(18, 1, 'mainul@gmail.com', '+8801635482457', '$2a$10$vAEz0l5WRM4.Li6Ej3.PF.SRSySxSwJIxIhROSxQkCRpJ8RSGGII2', 'mainul', NULL, NULL),
(20, 1, 'sab_hai07@yahoo.com', '01635482457', '$2a$10$49jRger3nL4/e3H280AscOihu66/vvYo0Sb6kFbdkdQb4d7TOFHv.', 'sabbir', NULL, NULL);


INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(13, 1),
(13, 2),
(17, 1),
(18, 2),
(20, 2);



