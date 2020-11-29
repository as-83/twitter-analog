package com.abdsul.sweeter.controller;

import com.abdsul.sweeter.entity.Message;
import com.abdsul.sweeter.entity.User;
import com.abdsul.sweeter.entity.dto.MessageDto;
import com.abdsul.sweeter.repos.MessageRepo;
import com.abdsul.sweeter.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
//@RequestMapping(path = "/message")
public class MessageController {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private MessageService messageService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greet( Model model){
        return "greeting";
    }


    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model,
                       @PageableDefault(sort = {"id"}, direction =  Sort.Direction.DESC) Pageable pageable,
                       @AuthenticationPrincipal User currentUser) {
        Page<MessageDto> page;

        page = messageService.messageList(pageable, filter, currentUser);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user, @Valid Message message, BindingResult bindingResult, Model model,
            @PageableDefault(sort = {"id"}, direction =  Sort.Direction.DESC) Pageable pageable,
            @RequestParam("file") MultipartFile file) throws IOException {
        message.setAuthor(user);

        if(bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);

        }else {
            saveFile(message, file);

            model.addAttribute("message", null);

            messageRepo.save(message);
        }

        Page<MessageDto> page = messageRepo.findAll(pageable, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");

        return "main";
    }

    private void saveFile(@Valid Message message, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-messages/{author}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            Model model,
            @PageableDefault(sort = {"id"}, direction =  Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Message message
    ){
        Page<MessageDto> page = messageService.messageListForUser( pageable, author, currentUser);
        model.addAttribute("page", page);
        model.addAttribute("url", "/user-messages/" + author.getId());
        model.addAttribute("userChannel", author);
        model.addAttribute("message", message);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String update(@AuthenticationPrincipal User currentUser,
                         @PathVariable Long user,
                         @RequestParam("id") Message message,
                         @RequestParam("text") String text,
                         @RequestParam("tag") String tag,
                         @RequestParam("file") MultipartFile file,
                         @PageableDefault(sort = {"id"}, direction =  Sort.Direction.DESC) Pageable pageable,
                         Model model
    ) throws IOException {
        if(message.getAuthor().equals(currentUser)){
            if(!StringUtils.isEmpty(text)){
                message.setText(text);
            }

            if(!StringUtils.isEmpty(tag)){
                message.setTag(tag);
            }

            saveFile(message, file);

            messageRepo.save(message);

        }

        return "redirect:/user-messages/" + user;
    }

    @GetMapping("/messages/{message}/like")
    public String like(@AuthenticationPrincipal User currentUser,
                       @PathVariable Message message,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer

    ){
        Set<User> likes = message.getLikes();
        if(likes.contains(currentUser)){
            likes.remove(currentUser);
        }else{
            likes.add(currentUser);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }

    @GetMapping("/messages/{message}/likes-list")
    public String likesList(Model model,
                            @PathVariable Message message){

        model.addAttribute("users", message.getLikes());
        return "subscriptions";
    }




}