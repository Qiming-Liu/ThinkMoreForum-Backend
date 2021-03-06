# [ThinkMoreForum-Backend](https://www.thinkmoreapp.com/)

![Watchers](https://img.shields.io/github/watchers/Qiming-Liu/ThinkMoreForum-Backend?style=social)
![Forks](https://img.shields.io/github/forks/Qiming-Liu/ThinkMoreForum-Backend?style=social)
![Stars](https://img.shields.io/github/stars/Qiming-Liu/ThinkMoreForum-Backend?style=social)

![LICENSE](https://img.shields.io/github/license/Qiming-Liu/ThinkMoreForum-Backend)
![Code Size](https://img.shields.io/github/languages/code-size/Qiming-Liu/ThinkMoreForum-Backend)
![Stars](https://img.shields.io/github/commit-activity/y/Qiming-Liu/ThinkMoreForum-Backend)

<p align="center"><img src="https://github.com/Qiming-Liu/ThinkMoreForum-Frontend/raw/main/public/3d.png"></p>

## [thinkmoreapp.com](https://www.thinkmoreapp.com/)

https://user-images.githubusercontent.com/68600416/167850826-1a155349-2daf-4b8b-b8d2-e6c9452d5020.mp4

ThinkMoreForum is a place for people to make any kind of discussion, where people come together to share their opinion. As a discussion platform, it is multifunctional, interactive, and mobile-ready. Administrators can easily set the look and topic of the forum, manage user permissions, and customize the site footer. The development process uses Jira as an agile scrum software to help the team organize and prioritize the work, and uses Github as a version control tool for a lightweight, branch-based workflow. From a technical perspective, the project uses message queues to send emails, and implements high-level JWT updates and protection, constructs WebSocket to achieve real-time online users and real-time notifications.  

## Project Architecture

<table align="center" border=0>
   <tr>
      <td width="500"><b>Front-end</b></td>
      <td width="500"><b>Back-end</b></td>
   </tr>
   <tr>
      <td>
         • Next.js<br>
         • Redux<br>
         • Typescript<br>
         • MUI<br>
         • Axios<br>
         • Husky<br>
         • Jest<br>
         • Eslint<br>
         • Sass-lint<br>
         • Prettier<br>
         • Yup, Formik
      </td>
      <td>
         • Spring boot<br>
         • Lombok & Mapstruct<br>
         • Flyway & PostgreSQL (JPA)<br>
         • Swagger & Postman<br>
         • Spring Security & JWT<br>
         • Jacoco & Mockito<br>
         • RabbitMQ (amqp)<br>
         • Amazon S3 (MinIO)<br>
         • Redis (Jedis)<br>
         • Sockjs (websocket)<br>
         • Sendgrid (Email)
      </td>
   </tr>
</table>  

![Project Architecture](https://github.com/Qiming-Liu/ThinkMoreForum-Frontend/raw/main/public/ThinkMore.svg)  

## Local Development

1. Use idea to open this project
2. ```docker-compose up -d```
3. Click Run or Debug

## [Grafana](https://g-d310600675.grafana-workspace.ap-southeast-2.amazonaws.com/)
> Account
* Username: Allen
* Password: Thinkmore-grafana1

