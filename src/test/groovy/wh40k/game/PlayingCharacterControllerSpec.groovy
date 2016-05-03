package wh40k.game

import grails.test.mixin.*
import spock.lang.*

@TestFor(PlayingCharacterController)
@Mock(PlayingCharacter)
class PlayingCharacterControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
		
		params["firstName"] = 'Enrico'
		params["middleName"] = 'boh'
		params["lastName"] = 'Meloni'
		params["experience"] = 400
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.playingCharacterList
            model.playingCharacterCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.playingCharacter!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def playingCharacter = new PlayingCharacter()
            playingCharacter.validate()
            controller.save(playingCharacter)

        then:"The create view is rendered again with the correct model"
            model.playingCharacter!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            playingCharacter = new PlayingCharacter(params)

            controller.save(playingCharacter)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/playingCharacter/show/1'
            controller.flash.message != null
            PlayingCharacter.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def playingCharacter = new PlayingCharacter(params)
            controller.show(playingCharacter)

        then:"A model is populated containing the domain instance"
            model.playingCharacter == playingCharacter
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def playingCharacter = new PlayingCharacter(params)
            controller.edit(playingCharacter)

        then:"A model is populated containing the domain instance"
            model.playingCharacter == playingCharacter
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/playingCharacter/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def playingCharacter = new PlayingCharacter()
            playingCharacter.validate()
            controller.update(playingCharacter)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.playingCharacter == playingCharacter

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            playingCharacter = new PlayingCharacter(params).save(flush: true)
            controller.update(playingCharacter)

        then:"A redirect is issued to the show action"
            playingCharacter != null
            response.redirectedUrl == "/playingCharacter/show/$playingCharacter.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/playingCharacter/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def playingCharacter = new PlayingCharacter(params).save(flush: true)

        then:"It exists"
            PlayingCharacter.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(playingCharacter)

        then:"The instance is deleted"
            PlayingCharacter.count() == 0
            response.redirectedUrl == '/playingCharacter/index'
            flash.message != null
    }
}
